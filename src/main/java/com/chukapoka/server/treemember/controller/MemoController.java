package com.chukapoka.server.treemember.controller;

import com.chukapoka.server.treemember.domain.Memo;
import com.chukapoka.server.treemember.domain.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/memo")
public class MemoController {

    private final ArrayList<Memo> list = new ArrayList<Memo>() {
        {
            add(new Memo(0, "content0", false));
            add(new Memo(1, "content1", false));
        }
    };

    @GetMapping("")
    public ResponseEntity<ArrayList<Memo>> list() {
        return ResponseEntity.ok(list);
    }

    @PostMapping("")
    public ResponseEntity<ResponseData> newItem() {
        int id = list.size();
        Memo newMemo = new Memo(id, "", false);
        list.add(newMemo);
        return ResponseEntity.ok(new ResponseData(id, true));
    }

    @PutMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<ResponseData> put(@PathVariable int id, @RequestBody Memo memo) {
        List<Memo> newList = list.stream().map(item -> item.id == id ? memo : item).toList();
        list.clear();
        list.addAll(newList);
        return ResponseEntity.ok(new ResponseData(id, true));
    }
}