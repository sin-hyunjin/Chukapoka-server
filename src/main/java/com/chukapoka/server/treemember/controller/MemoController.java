package com.chukapoka.server.treemember.controller;

import com.chukapoka.server.treemember.domain.Memo;
import com.chukapoka.server.treemember.domain.ResponseData;
import com.chukapoka.server.treemember.dto.TreeMemberDto;
import com.chukapoka.server.treemember.sevice.TreeMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private TreeMemberService treeMemberService;
    @PostMapping("/emailcheck")
    public ResponseEntity<String> emailCheck(@RequestBody TreeMemberDto treeMemberDto) {
        try {
            if (treeMemberService.emailCheck(treeMemberDto.getEmail())) {
                // 이메일이 존재하면 로그인 성공 처리를 수행
                // 여기에서는 간단히 "로그인 성공" 메시지를 반환
                return ResponseEntity.ok("이메일체크 성공");
            } else {
                // 이메일이 존재하지 않으면 로그인 실패 처리를 수행
                // 여기에서는 간단히 "로그인 실패" 메시지를 반환
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일체크 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일체크 중 오류 발생");
        }
    }

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
