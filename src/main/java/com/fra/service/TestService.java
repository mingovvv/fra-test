package com.fra.service;

import com.fra.entity.Channel;
import com.fra.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final JdbcTemplate jdbcTemplate;
    private final ChannelRepository channelRepository;

    public String test() {
        List<String> strings = jdbcTemplate.queryForList("select first_name from customers", String.class);
        System.out.println(strings);
        List<Channel> all = channelRepository.findAll();
        System.out.println(all);
        return "test";
    }

}
