package com.springmvc.springmongodbweb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created on 08/12/2017.
 *
 * @author Cesardl
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OperationNotAllowedException extends RuntimeException {
}
