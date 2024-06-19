package com.grpc.concept.s.Controller;

import com.grpc.concept.s.RestDto.*;
import com.grpc.concept.s.UiService.BookDetailsServ;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(tags = "BookDetailsRestEndpoints", description = "Endpoints for BookDetails grpc services")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookDetailsControllers {
    private BookDetailsServ bookDetailsServ;

    @PostMapping("/createBookDetails")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @ApiOperation(value = "BookDetails payload", notes = "Create a bookDetails")
    public  ResponseEntity<CreateBookDetailsResponseDto> createBookDet(@RequestHeader(value = "Authorization",required = false) String authorizationHeader,@RequestBody CreateBookDetails createBookDetails){
        String jwtToken=null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
        }
        return new ResponseEntity<>(bookDetailsServ.createB(createBookDetails,jwtToken),HttpStatus.CREATED);
    }

    @GetMapping("/getBookDetails/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @ApiOperation(value = "Get Book Id", notes = "Returns a BookDetails according to bookId")
    public ResponseEntity<GetBookDetailsResponse> getBookDetails(@RequestHeader(value="Authorization",required = false) String authorizationHeader,@PathVariable int id){

        String jwtToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
        }
        return  new ResponseEntity<>( bookDetailsServ.getB(id,jwtToken),HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/getAllBooks")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @ApiOperation(value = "Get pageSize and order params ", notes = "Return list of books")
    public ResponseEntity< List<GetAllBookResponseDto>> getAllBooks(@RequestHeader(value = "Authorization",required = false) String authorizationHeader,@RequestParam(value = "pageSize",required = false) String pageSize, @RequestParam(value = "order",required = false) String order){
        log.info("Get all books controller call");
        Map<String,String> requestParam=new HashMap<>();
            requestParam.put("pageSize",pageSize);
             requestParam.put("order",order);
        String jwtToken=null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
        }
        return   new ResponseEntity<>(bookDetailsServ.getAllB(requestParam,jwtToken),HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deleteBook/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @ApiOperation(value = "Get Book Id", notes = "Delete the book according to the bookId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void del(@RequestHeader(value="Authorization",required = false) String authorizationHeader,@PathVariable int id){
        String jwtToken=null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
        }
   bookDetailsServ.deleteB(id,jwtToken);
    }

    @PutMapping("/updateBook/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @ApiOperation(value = "Pass UpdateBookResponse Data transfer object", notes = "Update a book Details")
    public ResponseEntity< UpdateBookResponseDto> update(@RequestHeader(value="Authorization",required = false) String authorizationHeader,@RequestBody UpdateBookRequestDto updateBookRequestDto,@PathVariable int id){
        String jwtToken=null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
        }
        return new ResponseEntity<>( bookDetailsServ.updateBookMethod(updateBookRequestDto,id,jwtToken),HttpStatus.CREATED);
    }
}
