package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mapper.TariffMapper;
import org.example.repository.TariffRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffRepository tariffRepository;

    private final TariffMapper tariffMapper;
}
