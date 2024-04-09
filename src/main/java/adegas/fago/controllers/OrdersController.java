package adegas.fago.controllers;

import adegas.fago.helpers.GenKeyHelper;
import adegas.fago.helpers.HeadersHelper;
import adegas.fago.interfaces.KeysRepository;
import adegas.fago.interfaces.OrdersLocationRepository;
import adegas.fago.interfaces.OrdersRepository;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class OrdersController {
    @Autowired
    private OrdersRepository repository;
    @Autowired
    private KeysRepository keyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrdersLocationRepository ordersLocationRepository;

    private JSONObject IsAllowToOperate(Map<String, String> headers){
        String jwt = HeadersHelper.GetAccessTokenHeader(headers);
        JSONObject jsonObject = GenKeyHelper.VerifyJsonWebToken(jwt, keyRepository, false);
        if(jsonObject == null){
            return null;
        }
        return jsonObject;
    }

    @PostMapping(value="/orders")
    public ResponseEntity<Object> Create(@RequestBody OrderCollection payload, @RequestHeader Map<String, String> headers){
        JSONObject jsonObject = IsAllowToOperate(headers);
        if(jsonObject == null){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        payload.setCompanyId(jsonObject.getString("cid"));
        payload.setJailId(jsonObject.getString("jid"));
        repository.save(payload);
        return new ResponseEntity<>(payload, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderCollection>> Get(@RequestHeader Map<String, String> headers, @RequestParam long dateTimeStart, @RequestParam long dateTimeEnd){
        JSONObject jsonObject = IsAllowToOperate(headers);
        if(jsonObject == null){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        String jid = "";
        try{
            jid = jsonObject.getString("jid");
        } catch (Exception ignored){}
        if(jid.isEmpty()) {
            List<OrderCollection> list = repository.findBy(jsonObject.getString("cid"), dateTimeStart, dateTimeEnd);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        /*
        TODO: esta seccion es para futuro cuando se hagan las autoasignaciones de pedidos
        if(jsonObject.get("rol").equals("driver")){
            List<OrderCollection> list = repository.findBy(jsonObject.getString("oid"), jsonObject.getString("cid"), jid, dateTimeStart, dateTimeEnd);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        */
        List<OrderCollection> list = repository.findBy(jsonObject.getString("cid"), jid, dateTimeStart, dateTimeEnd);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/orders/{orderId}/status/{status}")
    public ResponseEntity<ResponseModel> SetStatus(@PathVariable String orderId, @PathVariable String status){
        ResponseModel response = new ResponseModel();
        OrderCollection order = repository.findOneById(orderId);
        if(order == null){
            response.setSuccess(false);
            response.setMessage("El pedido no existe");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        order.setStatus(status);
        repository.save(order);
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/orders/{orderId}/assign-to/{userId}")
    public ResponseEntity<ResponseModel> AssignTo(@PathVariable String orderId, @PathVariable String userId){
        ResponseModel response = new ResponseModel();
        OrderCollection order = repository.findOneById(orderId);
        if(order == null){
            response.setSuccess(false);
            response.setMessage("El pedido no existe");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        order.setAssignedTo(userId);
        repository.save(order);
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/orders/location")
    public ResponseEntity<ResponseModel> SetOrderLocation(@RequestBody Map<String, Object> payload){
        ResponseModel response = new ResponseModel();
        OrderCollection order = repository.findOneById((String)payload.get("id"));
        if(order == null){
            response.setSuccess(false);
            response.setMessage("El pedido no existe");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        OrdersLocationCollection ordersLocationCollection = new OrdersLocationCollection();
        ordersLocationCollection.setOrderId(order.getID());
        ordersLocationCollection.setLatLng((List<Double>)payload.get("coords"));
        ordersLocationCollection.setBearing(Float.parseFloat(String.valueOf(payload.get("bearing"))));
        ordersLocationCollection.setTimeStamp(Long.parseLong((String.valueOf(payload.get("timestamp")))));
        ordersLocationRepository.save(ordersLocationCollection);
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/orders/location/{orderId}")
    public ResponseEntity<List<OrdersLocationCollection>> GetLocation(@PathVariable String orderId){
        List<OrdersLocationCollection> list = ordersLocationRepository.findLocationsByOrderId(orderId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/orders/day-resume/{userId}")
    public ResponseEntity<List<DayResume>> GetDayResume(@RequestHeader Map<String, String> headers, @PathVariable String userId, @RequestParam long dateTimeStart, @RequestParam long dateTimeEnd){
        JSONObject jsonObject = IsAllowToOperate(headers);
        if(jsonObject == null){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        String cid = jsonObject.getString("cid");
        List<OrderCollection> list = repository.findBy(cid, userId, dateTimeStart, dateTimeEnd,true);
        List<DayResume> listResume = new ArrayList<>();
        for(OrderCollection order: list){
            DayResume dayResume = new DayResume();
            dayResume.setPhone(order.getPhone());
            dayResume.setAddress(order.getAddress());

            Integer[] quantities = new Integer[]{0,0,0,0,0};
            ArrayList<String> paymentsType = new ArrayList<>();
            float total = 0;
            for(OrderCollectionItems item: order.getItems()){
                boolean add = true;
                for(String pt: paymentsType){
                    if(pt.equals(item.getPaymentType())){
                        add = false;
                        break;
                    }
                }
                if(add){
                    paymentsType.add(item.getPaymentType());
                }
                total += item.getTotalPrice();
                switch (item.getProduct()) {
                    case "5K":
                        quantities[0] = item.getQuantity();
                        break;
                    case "11K":
                        quantities[1] = item.getQuantity();
                        break;
                    case "15K":
                        quantities[2] = item.getQuantity();
                        break;
                    case "45K":
                        quantities[3] = item.getQuantity();
                        break;
                    case "Aluminio":
                        quantities[4] = item.getQuantity();
                        break;
                }
            }
            dayResume.setTypePayment(String.join(",", paymentsType));
            dayResume.setValue(total);
            dayResume.setQuantities(quantities);

            listResume.add(dayResume);
        }
        return new ResponseEntity<>(listResume, HttpStatus.OK);
    }

}
