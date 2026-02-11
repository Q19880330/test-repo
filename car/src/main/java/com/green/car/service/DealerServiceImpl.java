package com.green.car.service;

import com.green.car.entity.Dealer;
import com.green.car.repository.DealerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DealerServiceImpl implements DealerService{

    private final DealerRepository dealerRepository;

    @Override
    public Dealer getDealer(Long dealerId) {
        Dealer dealer = dealerRepository.findById(dealerId).get();
        return dealer;
    }
}
