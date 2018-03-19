package rizki.practicum.learning.controller;
/*
    Created by : Rizki Maulana Akbar, On 03 - 2018 ;
*/

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import rizki.practicum.learning.dto.ResponseObject;

import javax.servlet.http.HttpServletResponse;
import java.util.zip.DataFormatException;

@RestControllerAdvice
public class WebResponse implements ApplicationEventPublisherAware{
    protected ApplicationEventPublisher applicationEventPublisher;
    public static final int  DEFAULT_PAGE_SIZE = 100;
    public static final int DEFAULT_PAGE_NUM = 0;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public static <T> T checkNullObject ( T object ) {
        if (object == null) throw new ResourceNotFoundException("Objek tidak ditemukan");
        return object;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileFormatException.class)
    public
    @ResponseBody
    ResponseObject handleDataFormatException(FileFormatException ex, WebRequest request, HttpServletResponse response) {
        return ResponseObject
                .builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileUploadException.class)
    public
    @ResponseBody
    ResponseObject handleFileUploadException(FileUploadException ex, WebRequest request, HttpServletResponse response) {
        return ResponseObject
                .builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataFormatException.class)
    public
    @ResponseBody
    ResponseObject handleDataStoreException(DataFormatException ex, WebRequest request, HttpServletResponse response) {
        return ResponseObject
                .builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public
    @ResponseBody
    ResponseObject handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request, HttpServletResponse response) {
        return ResponseObject
                .builder()
                .code(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public
    @ResponseBody
    ResponseObject handleResourcesNotFoundException(ResourceNotFoundException ex, WebRequest request, HttpServletResponse response) {
        return ResponseObject
                .builder()
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public
    @ResponseBody
    ResponseObject handleIllegalArgumentException(DataFormatException ex, WebRequest request, HttpServletResponse response) {
        return ResponseObject
                .builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

}
