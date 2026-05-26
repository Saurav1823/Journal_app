package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

   @Autowired
   private JournalEntryService journalEntryService;

   @Autowired
   private UserService userService;


    @GetMapping("{userName}")
    public ResponseEntity<?> getJournalEntriesByUserName(@PathVariable String userName){
         User user = userService.findByUsername(userName);
        List<JournalEntry>all =  user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry , @PathVariable String userName){
        try {
            //User user = userService.findByUsername(userName);
            System.out.println("In journal Controller");
            journalEntryService.saveEntry(myEntry,userName);
            System.out.println("In journal Controller2");

            return new ResponseEntity<>(myEntry ,HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println("In journal exception");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId ){

        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String userName, @PathVariable ObjectId myId ){
            journalEntryService.deleteById(myId, userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;

    }

    @PutMapping("/id/{userName}/{id}")
    public ResponseEntity<?>  updateJournalById(@PathVariable ObjectId id ,
                                                @RequestBody JournalEntry newEntry
                                              ,@PathVariable String userName){
        JournalEntry old = journalEntryService.findById(id).orElse(null);

        if(old != null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
