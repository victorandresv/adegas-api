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
import java.util.HashMap;
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
    public ResponseEntity<Map<String, Object>> GetDayResume(@RequestHeader Map<String, String> headers, @PathVariable String userId, @RequestParam long dateTimeStart, @RequestParam long dateTimeEnd){
        JSONObject jsonObject = IsAllowToOperate(headers);
        if(jsonObject == null){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        String cid = jsonObject.getString("cid");
        List<OrderCollection> list = null;
        if(jsonObject.getString("rol").equals("central")){
            String jailId = jsonObject.getString("jid");
            list = repository.findBy(cid, jailId, dateTimeStart, dateTimeEnd);
        } else {
            list = repository.findBy(cid, userId, dateTimeStart, dateTimeEnd,true);
        }
        List<DayResume> listResume = new ArrayList<>();
        float globalTotal = 0;
        for(OrderCollection order: list){
            for(OrderCollectionItems orderItems: order.getItems()){
                DayResume dayResume = new DayResume();
                dayResume.setPhone(order.getPhone());
                dayResume.setProduct(orderItems.getProduct());
                if(orderItems.getDiscountCode() == null){
                    dayResume.setDiscountCode("");
                } else {
                    dayResume.setDiscountCode(orderItems.getDiscountCode());
                }
                dayResume.setQuantity(orderItems.getQuantity());
                dayResume.setTotal(orderItems.getTotalPrice());
                dayResume.setPaymentType(orderItems.getPaymentType());
                listResume.add(dayResume);
                globalTotal += orderItems.getTotalPrice();
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("items", listResume);
        result.put("total", globalTotal);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
