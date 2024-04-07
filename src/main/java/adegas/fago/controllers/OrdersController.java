package adegas.fago.controllers;

import adegas.fago.helpers.GenKeyHelper;
import adegas.fago.helpers.HeadersHelper;
import adegas.fago.interfaces.KeysRepository;
import adegas.fago.interfaces.OrdersLocationRepository;
import adegas.fago.interfaces.OrdersRepository;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.OrderCollection;
import adegas.fago.models.OrdersLocationCollection;
import adegas.fago.models.ResponseModel;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        payload.setStatus("new");
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
        if(jsonObject.get("rol").equals("driver")){
            List<OrderCollection> list = repository.findBy(jsonObject.getString("oid"), jsonObject.getString("cid"), jid, dateTimeStart, dateTimeEnd);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        List<OrderCollection> list = repository.findBy(jsonObject.getString("cid"), jid, dateTimeStart, dateTimeEnd);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/orders/{orderId}/status/{status}")
    public ResponseEntity<ResponseModel> SetStatus(@PathVariable String orderId, @PathVariable String status){
        ResponseModel response = new ResponseModel();
        OrderCollection user = repository.findOneById(orderId);
        if(user == null){
            response.setSuccess(false);
            response.setMessage("El pedido no existe");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        user.setStatus(status);
        repository.save(user);
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

}
