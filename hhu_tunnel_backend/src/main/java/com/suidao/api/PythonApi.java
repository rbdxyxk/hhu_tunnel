package com.suidao.api;

import com.suidao.base.SysConf;
import com.suidao.config.javaToPython;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("python")
public class PythonApi {

    @GetMapping("/use")
    public void pythonUse() throws IOException, InterruptedException {
        javaToPython command = new javaToPython();
        command.getFile(SysConf.pythonFilepath);
    }
}
