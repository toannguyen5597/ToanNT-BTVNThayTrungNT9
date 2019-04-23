package com.Dictionary.ToanNT.controller;

import com.Dictionary.ToanNT.data.model.Word;
import com.Dictionary.ToanNT.data.service.WordService;
import com.Dictionary.ToanNT.data.service.WordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

@Controller
public class controller {
    @Autowired
    WordServiceImpl wordService;

    List<Word> list;

//    @RequestMapping(value = {"/", "/index"})
//    public String homePage(Model model){
//        User user= new User();
//        model.addAttribute("user", user);
//        return "redirect:login";
//    }

    @GetMapping("/getall")
    public void getAll(){
        list = wordService.getAll();
        list.forEach(System.out::println);
    }

    @GetMapping("/import/{path}")
    public void importFile(@PathVariable("path")String path){
        try {
            BufferedReader br = new BufferedReader(new FileReader(path+"/"+"input"));
            String line = br.readLine();
            int dem = 0;
            while(line != null){
                String ar[] = line.split(":");
                if(ar.length > 1){
                    Word word = new Word();
                    word.setKey(ar[0]);
                    word.setMean(ar[1]);
                    word.setType("Việt - Anh");
//                    wordService.updateOrInsertWord(word);
//                    System.out.println(dem);
                    dem++;
                    if(dem > 47733){
                        wordService.updateOrInsertWord(word);
                    }
                }
                line = br.readLine();
            }
            System.out.println(dem);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("/update")
    public boolean updateWord(@RequestBody Word word){
        try {
            if(word.getKey() != null && word.getMean() != null && word.getType() != null){
                wordService.updateOrInsertWord(word);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/search/{key}")
    public void getWordByKey(@PathVariable("key")String key){
        try {
            if(wordService.getWordByKey(list,key) != null){
                List<Word> myListByKey = wordService.getWordByKey(list,key);
                myListByKey.forEach(System.out::println);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("/delete")
    public boolean deleteWord(@RequestBody Word word){
        System.out.println(word);
        try {
            wordService.deleteWord(word);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


//    @PostMapping("/login")
//    public String login(){
//        System.out.println("ok");
//        return "";
//    }
}
