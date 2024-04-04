package adegas.fago.controllers;

import adegas.fago.interfaces.LocationRepository;
import adegas.fago.interfaces.UserLocationRepository;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.LocationCollection;
import adegas.fago.models.UserLocationCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LocationController {

    @Autowired
    private LocationRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserLocationRepository userLocationRepository;

    @PostMapping(value="/location")
    public ResponseEntity<LocationCollection> Create(@RequestBody LocationCollection payload){
        repository.save(payload);
        return new ResponseEntity<>(payload, HttpStatus.CREATED);
    }

    @PostMapping(value="/location/drivers")
    public ResponseEntity<List<UserLocationCollection>> SetDriverLocations(@RequestBody List<UserLocationCollection> payload){

        List<UserLocationCollection> results = new ArrayList<>();

        for(UserLocationCollection item: payload){
            UserLocationCollection result = userLocationRepository.findOneByIdAndTs(item.getUserId(), item.getTimestamp());
            if(result == null){
                UserLocationCollection saveResults = userLocationRepository.save(item);
                results.add(saveResults);
            } else {
                results.add(result);
            }
        }

        return new ResponseEntity<>(results, HttpStatus.CREATED);
    }
}
