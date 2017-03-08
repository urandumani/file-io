package com.ekipa.util;

import com.ekipa.exception.InternalServerErrorException;
import com.ekipa.service.FileService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FileUtil {
    private static final Logger logger = Logger.getLogger(FileService.class);

    public Path getPath(String id) {
        return Paths.get(id);
    }

    public boolean fileExists(Path path) {
        return Files.exists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
    }

    public boolean fileNotExists(Path path) {
        return Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
    }

    public BasicFileAttributes getFileAttributes(Path path) throws InternalServerErrorException {
        try {
            BasicFileAttributeView attributeView = Files.getFileAttributeView(path, BasicFileAttributeView.class);
            if (attributeView != null) {
                if (attributeView.readAttributes() == null) {
                    throw new InternalServerErrorException("Could not read file attributes of " + path.getFileName());
                }
                return attributeView.readAttributes();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerErrorException("Could not read file attributes");
        }
        return null;
    }

    public Set<Path> getDirectories(Path path) throws InternalServerErrorException {
        return getFiles(path, true);
    }

    public Set<Path> getFiles(Path path) throws InternalServerErrorException {
        return getFiles(path, false);
    }

    private Set<Path> getFiles(Path path, boolean filterDirectories) throws InternalServerErrorException {
        Set<Path> files = new HashSet<Path>();
        try {
            if (path != null) {
                if (filterDirectories) {
                    files.addAll(Files.list(path).filter(Files::isDirectory).collect(Collectors.toSet()));
                } else {
                    files.addAll(Files.list(path).filter(Files::isRegularFile).collect(Collectors.toSet()));
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerErrorException("Unable to read files/directories in path " + path.getFileName().toString());
        }
        return files;
    }
}
