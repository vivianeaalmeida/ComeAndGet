package org.upskill.springboot.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Base controller for all API endpoints.
 *
 * This abstract class provides a common base for all controllers in the application,
 * setting the base URL for all API endpoints to "/api/v1".
 */
@RequestMapping("/api/v1")
@CrossOrigin("*")
public abstract class BaseController {
}