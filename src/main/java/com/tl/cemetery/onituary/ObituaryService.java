package com.tl.cemetery.onituary;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ObituaryService {

    private ModelMapper modelMapper;

    private ObituaryRepository repository;


}
