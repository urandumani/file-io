package com.ekipa.resource;

import static com.ekipa.constant.WebDefinitions.CONTENT_BY_ID;
import static com.ekipa.constant.WebDefinitions.FILE;
import static com.ekipa.constant.WebDefinitions.ID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ekipa.model.MessageModel;
import com.ekipa.service.FileService;

@RestController
@RequestMapping(CONTENT_BY_ID)
public class FileContentResource {

    @Autowired
    private FileService fileService;

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getFileContent(@PathVariable(ID) String id) throws Exception {
        return fileService.getFileContent(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageModel createDirectory(@PathVariable(ID) String id) throws Exception {
        fileService.createDirectory(id);
        return MessageModel.message("The directory is created");
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public MessageModel createFile(@PathVariable(ID) String id, @RequestParam(FILE) MultipartFile file) throws Exception {
        fileService.createFile(id, file);
        return MessageModel.message("The file is created");
    }

    @PostMapping(consumes = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public MessageModel createFile(@PathVariable(ID) String id, @RequestBody byte[] content) throws Exception {
        fileService.createFile(id, content);
        return MessageModel.message("The file is created");
    }

    @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public MessageModel updateFile(@PathVariable(ID) String id, @RequestParam(FILE) MultipartFile file) throws Exception {
        fileService.updateFile(id, file);
        return MessageModel.message("The file is uploaded/overwritten");
    }

    @PutMapping(consumes = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public MessageModel updateFile(@PathVariable(ID) String id, @RequestBody byte[] content) throws Exception {
        fileService.updateFile(id, content);
        return MessageModel.message("The file is uploaded/overwritten");
    }
}
