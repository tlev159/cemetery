package com.tl.cemetery.grave;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GraveService {

    private ModelMapper modelMapper;

    private GraveRepository repository;


}
