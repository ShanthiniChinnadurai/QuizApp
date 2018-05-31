package com.kg.convert.convert.controller;

import java.util.List;

import com.kg.convert.convert.model.Convert;
import com.kg.convert.convert.model.User;
import com.kg.convert.convert.service.convertservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.*;
import java.net.Authenticator;
import java.util.*;


@RestController
@RequestMapping("/convert")
@CrossOrigin(origins = "*")
public class convertcontroller {
  @Autowired
  private convertservice cs;

  @RequestMapping(value="/save")
  public ResponseEntity<?> saveFlight(@RequestBody Convert r,UriComponentsBuilder builder){
    
  //  System.out.println(r.getCategory()+" "+r.getCode()+" "+r.getOptions());
        return new ResponseEntity<>(cs.saveRegister(r),HttpStatus.OK);
  }


  @GetMapping("/getall")
  public @ResponseBody ResponseEntity<List<Convert>> all() {

      return new ResponseEntity<List<Convert>>(cs.getAll(), HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public Convert getRegister(@PathVariable long id) {
      return cs.findOne(id);
  }

  @RequestMapping(value="/savemail",method=RequestMethod.POST)
  public ResponseEntity<?> saveMail(@RequestBody User register,UriComponentsBuilder builder){
    User reg=register;     
    String cat=String.valueOf(reg);
    System.out.println(cat);
    //  System.out.println(r.getCategory()+" "+r.getCode()+" "+r.getOptions());
        return new ResponseEntity<>(cat,HttpStatus.OK);
  }

@RequestMapping(value="/sendmail/{marks}", method=RequestMethod.POST)
    public ResponseEntity<?> sendmail(@PathVariable String marks,@RequestBody User register)
    {   
        System.out.println("++++++++++++++"+marks);  
        System.out.println("++++++++++++sendmail+++++++++++");
        User reg=register;           
          String toMail= reg.getEmail();
             //static String  recipientAddress[];
    
        try {
            convertcontroller javaEmail = new convertcontroller();
           
         javaEmail.sendEmail(toMail,marks); 
            System.out.println("Process Completed\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
      System. out.println("event registered successfully");
    

        return new ResponseEntity<User>(register,HttpStatus.OK);
    } 

      static String  recipientAddress[];   

    public  void sendEmail(String to,String marks)
    {
    final String username = "baraneetharan.ramasamy@kgfsl.com";
    final String password = "Barani@1234";
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "false");
    props.put("mail.smtp.host", "webmail.kggroup.com");
    props.put("mail.smtp.port", "25");
    Session session = Session.getInstance(props,
    new javax.mail.Authenticator() {
    protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(username, password);
    }
    });
    try {        
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress("baraneetharan.ramasamy@kgfsl.com"));
    message.setRecipients(Message.RecipientType.TO,
    InternetAddress.parse(to));
    message.setSubject("Quiz results");
    message.setText("Dear candidate,"
    + "Your mark is"+marks);
    Transport.send(message);
    System.out.println("Done");
    }
    catch (MessagingException e) 
    {
    throw new RuntimeException(e);
    }
    }


}