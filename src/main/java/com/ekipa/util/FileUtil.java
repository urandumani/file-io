package com.ekipa.util;

import com.ekipa.exception.FileAlreadyInUseException;
import com.ekipa.exception.InternalServerErrorException;
import com.ekipa.service.FileService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
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

    public boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }

    public byte[] readBytes(Path path) throws InternalServerErrorException {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerErrorException("Unable to read bytes from the given file, please try again");
        }
    }

    public boolean isNotWritable(Path path) {
        return !Files.isWritable(path);
    }

    public boolean fileExists(Path path) {
        return Files.exists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
    }

    public boolean fileNotExists(Path path) {
        return Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
    }

    public void createDirectories(Path path) throws InternalServerErrorException {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerErrorException("Unable to create directory with id:" + path.toString());
        }
    }

    public void createFile(Path path, byte[] content) throws InternalServerErrorException {
        if (path.getParent() != null) {
            createDirectories(path.getParent());
        }
        try {
            Files.write(path, content);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerErrorException("Unable to write in file with id:" + path.toString());
        }
    }

    public void updateFile(Path path, byte[] content) throws FileAlreadyInUseException, InternalServerErrorException {
        try {
            FileChannel channel = FileChannel.open(path);
            FileLock lock = channel.tryLock();
            channel.write(ByteBuffer.wrap(content));
            lock.release();
        } catch (OverlappingFileLockException e) {
            logger.error(e.getMessage(), e);
            throw new FileAlreadyInUseException("File with id:" + path.getFileName() + " is in use by another process, please try later");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerErrorException("Unable to open a channel to a file file with id:" + path.toString());
        }
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

    public void deleteFile(Path path) throws InternalServerErrorException {
        try {
            Files.delete(path);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerErrorException("Was unable to delete file with id:" + path.toString());
        }
    }
}
