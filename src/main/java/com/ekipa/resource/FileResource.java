package com.ekipa.resource;

import static com.ekipa.constant.WebDefinitions.FILE_INFO_BY_ID;
import static com.ekipa.constant.WebDefinitions.ID;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekipa.exception.InternalServerErrorException;
import com.ekipa.model.FileModel;
import com.ekipa.model.MessageModel;
import com.ekipa.service.FileService;

@RestController
@RequestMapping(FILE_INFO_BY_ID)
public class FileResource {

    @Autowired
    private FileService fileService;

    @GetMapping
    public FileModel getFileInfo(@PathVariable(ID) final String id) throws InternalServerErrorException, FileNotFoundException {
        return fileService.findFile(id);
    }

    @DeleteMapping
    public MessageModel deleteFile(@PathVariable(ID) String id) throws Exception {
        fileService.deleteFile(id);
        return MessageModel.message("File with the requested ID: " + id + ", has been deleted.");
    }
}
