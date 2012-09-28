package ee.elisa.gamechannel.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RESTException {

		/**
		 * List of customized modules and error codes constants
		 */
		public static final String XAPIERROR_PARTY_EXISTS = "EPD_1000",
				XAPIERROR_PARTY_ID_NOT_SPECIFIED = "EPD_1010",
				XAPIERROR_PARTY_INVALID_DATA = "EPD_1020",
				XAPIERROR_PARTY_NOT_EXISTS = "EPD_1030",
				XAPIERROR_PARTY_PARAMETER_UPDATE_FAILURE = "EPD_1040",
				XAPIERROR_OFFER_VALIDATION_FAILURE = "EPD_1100",
				XAPIERROR_OFFER_CREATION_FAILURE = "EPD_1110";

		public static class NotFoundException extends WebApplicationException {

			/**
			 * Specified REST resource not found
			 *
			 * @param msg
			 */
			public NotFoundException(String msg) {
				super(Response.status(Response.Status.NOT_FOUND).entity(msg)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public NotFoundException(String msg, Throwable t) {
				super(Response.status(Response.Status.NOT_FOUND).entity(msg)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public NotFoundException(Throwable t) {
				super(Response.status(Response.Status.NOT_FOUND)
						.entity(t.getMessage()).type(MediaType.TEXT_PLAIN).build());
			}
		}

		/**
		 * Semantics errors like forbidden product hierarcy
		 *
		 * @author tonisa
		 */
		public static class ForbiddenException extends WebApplicationException {

			public ForbiddenException(String msg) {
				super(Response.status(Response.Status.FORBIDDEN).entity(msg)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public ForbiddenException(String msg, Throwable t) {
				super(Response.status(Response.Status.FORBIDDEN).entity(msg)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public ForbiddenException(Throwable t) {
				super(Response.status(Response.Status.FORBIDDEN)
						.entity(t.getMessage()).type(MediaType.TEXT_PLAIN).build());
			}

			public ForbiddenException(String msg, String moduleErrorCode) {
				super(Response.status(Response.Status.FORBIDDEN).entity(msg)
						.header("X-API-Error", moduleErrorCode)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public ForbiddenException(String msg, Throwable t,
					String moduleErrorCode) {
				super(Response.status(Response.Status.FORBIDDEN).entity(msg)
						.header("X-API-Error", moduleErrorCode)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public ForbiddenException(Throwable t, String moduleErrorCode) {
				super(Response.status(Response.Status.FORBIDDEN)
						.header("X-API-Error", moduleErrorCode)
						.entity(t.getMessage()).type(MediaType.TEXT_PLAIN).build());
			}

		}

		/**
		 * Syntax errors and invalid id's in incoming data
		 *
		 * @author tonisa
		 */
		public static class BadRequestException extends WebApplicationException {

			public BadRequestException(String msg) {
				super(Response.status(Response.Status.BAD_REQUEST).entity(msg)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public BadRequestException(String msg, Throwable t) {
				super(Response.status(Response.Status.BAD_REQUEST).entity(msg)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public BadRequestException(Throwable t) {
				super(Response.status(Response.Status.BAD_REQUEST)
						.entity(t.getMessage()).type(MediaType.TEXT_PLAIN).build());
			}

			public BadRequestException(String msg, String moduleErrorCode) {
				super(Response.status(Response.Status.BAD_REQUEST).entity(msg)
						.header("X-API-Error", moduleErrorCode)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public BadRequestException(String msg, Throwable t,
					String moduleErrorCode) {
				super(Response.status(Response.Status.BAD_REQUEST).entity(msg)
						.header("X-API-Error", moduleErrorCode)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public BadRequestException(Throwable t, String moduleErrorCode) {
				super(Response.status(Response.Status.BAD_REQUEST)
						.entity(t.getMessage())
						.header("X-API-Error", moduleErrorCode)
						.type(MediaType.TEXT_PLAIN).build());
			}
		}

		
		/**
		 * Syntax errors and invalid id's in incoming data
		 *
		 * @author tonisa
		 */
		public static class SystemErrorException extends WebApplicationException {

			static Response.Status code = Response.Status.INTERNAL_SERVER_ERROR;
			
			public SystemErrorException(String msg) {
				super(Response.status(code).entity(msg)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public SystemErrorException(String msg, Throwable t) {
				super(Response.status(code).entity(msg)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public SystemErrorException(Throwable t) {
				super(Response.status(code)
						.entity(t.getMessage()).type(MediaType.TEXT_PLAIN).build());
			}

			public SystemErrorException(String msg, String moduleErrorCode) {
				super(Response.status(code).entity(msg)
						.header("X-API-Error", moduleErrorCode)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public SystemErrorException(String msg, Throwable t,
					String moduleErrorCode) {
				super(Response.status(code).entity(msg)
						.header("X-API-Error", moduleErrorCode)
						.type(MediaType.TEXT_PLAIN).build());
			}

			public SystemErrorException(Throwable t, String moduleErrorCode) {
				super(Response.status(Response.Status.ACCEPTED)
						.entity(t.getMessage())
						.header("X-API-Error", moduleErrorCode)
						.type(MediaType.TEXT_PLAIN).build());
			}
		}			
}
