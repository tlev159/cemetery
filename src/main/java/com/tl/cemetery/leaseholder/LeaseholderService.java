package com.tl.cemetery.leaseholder;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LeaseholderService {

    private ModelMapper modelMapper;

    private LeaseholderRepository repository;


}
