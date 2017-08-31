package com.uploadfile.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.uploadfile.Prop;
import com.uploadfile.exception.StException;
import com.uploadfile.exception.NfException;

@Service
public class FileSvImpl implements FsService {

    private final Path rootLocation;

    @Autowired
    public FileSvImpl(Prop properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void upload(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StException("Can't persist empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StException("Can't Persist file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StException("Can't read files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new NfException("Can't read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new NfException("Can't read file: " + filename, e);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StException("Can't init storage", e);
        }
    }
}
