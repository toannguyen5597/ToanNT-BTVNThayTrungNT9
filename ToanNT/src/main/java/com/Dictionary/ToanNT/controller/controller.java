package com.Dictionary.ToanNT.controller;

import com.Dictionary.ToanNT.data.model.User;
import com.Dictionary.ToanNT.data.model.Word;
import com.Dictionary.ToanNT.data.service.WordService;
import com.Dictionary.ToanNT.data.service.WordServiceImpl;
import com.Dictionary.ToanNT.viewmodel.ListDetail;
import com.Dictionary.ToanNT.viewmodel.UserDetail;
import com.Dictionary.ToanNT.viewmodel.WordDetail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Controller("/")
public class controller {
    @Autowired
    WordServiceImpl wordService;

    List<Word> list;

    //@GetMapping("/getall")
    public List<Word> getAll(){
        return wordService.getAll();
        //list.forEach(System.out::println);
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

    @GetMapping("/di{key}")
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

    @PostMapping(value = "/direc")
    public String deleteWord(Model model, @RequestParam("id") Integer id){
        try {
            wordService.deleteWord(id);
            ListDetail vm = listDetail();
            model.addAttribute("vm", vm);
            return "mydirect";
        }catch (Exception e){
            e.printStackTrace();
            return "mydirect";
        }
    }

    @GetMapping("/")
    public String getIndex(){
        return "login";
    }

    public ListDetail listDetail(){
        ModelMapper modelMapper = new ModelMapper();
        ListDetail vm = new ListDetail();
        ArrayList<WordDetail> wordDetails = new ArrayList<>();
        List<Word> word = getAll();
        for(Word word1 : word) {
            WordDetail wordDetail = new WordDetail();
            wordDetail = modelMapper.map(word1, WordDetail.class);
            wordDetails.add(wordDetail);
        }
        vm.setListWordDetail(wordDetails);
        return vm;
    }

    @RequestMapping(value = "/direct", method = RequestMethod.POST)
        public String login(Model model, @RequestParam("username") String username, @RequestParam("password") String password){
        User user = wordService.login(username, password);
        if(user != null){
            ListDetail vm = listDetail();
            model.addAttribute("vm", vm);
            return "mydirect";
        }
        return "login";
    }
}
