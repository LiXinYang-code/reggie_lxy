package com.lxyproject_exercise.reggie_lxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxyproject_exercise.reggie_lxy.entity.AddressBook;
import com.lxyproject_exercise.reggie_lxy.mapper.AddressBookMapper;
import com.lxyproject_exercise.reggie_lxy.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
