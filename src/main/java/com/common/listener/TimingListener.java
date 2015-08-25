package com.common.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;  
import org.springframework.stereotype.Component;

import com.service.BookService;  

@Component
public class TimingListener {
	
	@Autowired
	public BookService bookService;
	
    @Scheduled(cron="0 0 23 * * ? ") //每天晚上11点执行
    public void taskCycle(){
        System.out.println("开始执行更新图书的状态");
        this.bookService.updateBookState();
        System.out.println("更新图书的状态完毕");
    }
}