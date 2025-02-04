package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequestMapping("/journal")
public class JournalEntityController {


    @Autowired
    private JournalEntryService journalentryservice;
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getAllGeneralEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty())
        {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> postEntry(@RequestBody JournalEntry abc)
    {
        try{
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String userName = authentication.getName();
                journalentryservice.SaveEntry(abc,userName);
                return new ResponseEntity<JournalEntry>(abc, HttpStatus.OK);
            }catch(Exception e) {

            return new ResponseEntity<JournalEntry>( HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{ID}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId ID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity byUserName = userService.findByUserName(userName);
        List<JournalEntry> collect = byUserName.getJournalEntries().stream().filter(x -> x.getId().equals(ID)).collect(Collectors.toList());

        if(!collect.isEmpty())
        {
            Optional<JournalEntry> byId = journalentryservice.findById(ID);
            if(byId.isPresent())
            {
                return new ResponseEntity<JournalEntry>(byId.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/id/{ID}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId ID){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            boolean check = journalentryservice.DeleteById(ID,userName);
            if(check)
            {
                return new ResponseEntity<JournalEntry>(HttpStatus.NO_CONTENT);
            }
            else
            {
                return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
            }

    }

    @PutMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId myid,
                                                          @RequestBody JournalEntry entry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity byUserName = userService.findByUserName(userName);
        List<JournalEntry> collect = byUserName.getJournalEntries().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty())
        {
            Optional<JournalEntry> byId = journalentryservice.findById(myid);
            if(byId.isPresent())
            {
                JournalEntry jn = byId.get();
                jn.setTitle(entry.getTitle()!=null && !entry.getTitle().equals("")?entry.getTitle(): jn.getTitle());
                jn.setContent(entry.getContent()!=null && !entry.getContent().equals("")?entry.getContent(): jn.getContent());
                journalentryservice.SaveEntry(jn);
                return new ResponseEntity<JournalEntry>( jn,HttpStatus.OK);
            }
        }


        return new ResponseEntity<JournalEntry>( HttpStatus.NO_CONTENT);
    }
}
