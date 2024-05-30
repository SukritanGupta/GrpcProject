package com.grpc.concept.s.Controller;

import com.grpc.concept.s.RestDto.*;
import com.grpc.concept.s.UiService.BookDetailsServ;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(tags = "BookDetailsRestEndpoints", description = "Endpoints for BookDetails grpc services")
@AllArgsConstructor
//@SuppressWarnings("deprecation")
public class BookDetailsControllers {
//    @Autowired
    private BookDetailsServ bookDetailsServ;

    @PostMapping("/createBookDetails")
    @ApiOperation(value = "BookDetails payload", notes = "Create a bookDetails")
    public  ResponseEntity<CreateBookDetailsResponseDto> createBookDet(@RequestBody CreateBookDetails createBookDetails){
        return new ResponseEntity<>(bookDetailsServ.createB(createBookDetails),HttpStatus.CREATED);
    }

    @GetMapping("/getBookDetails/{id}")
    @ApiOperation(value = "Get Book Id", notes = "Returns a BookDetails according to bookId")
    public ResponseEntity<GetBookDetailsResponse> getBookDetails(@PathVariable int id){

        return  new ResponseEntity<>( bookDetailsServ.getB(id),HttpStatus.OK);
    }

    @GetMapping("/getAllBooks")
    @ApiOperation(value = "Get pageSize and order params ", notes = "Return list of books")
    public ResponseEntity< List<GetAllBookResponseDto>> getAllBooks(@RequestParam(value = "pageSize",required = false) String pageSize, @RequestParam(value = "order",required = false) String order){
        Map<String,String> requestParam=new HashMap<>();
            requestParam.put("pageSize",pageSize);
             requestParam.put("order",order);
        return   new ResponseEntity<>(bookDetailsServ.getAllB(requestParam),HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook/{id}")
    @ApiOperation(value = "Get Book Id", notes = "Delete the book according to the bookId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void del(@PathVariable int id){
   bookDetailsServ.deleteB(id);
    }

//    may be in future  logic change
    @PutMapping("/updateBook/{id}")
    @ApiOperation(value = "Pass UpdateBookResponse Data transfer object", notes = "Update a book Details")
    public ResponseEntity< UpdateBookResponseDto> update(@RequestBody UpdateBookRequestDto updateBookRequestDto,@PathVariable int id){
        return new ResponseEntity<>( bookDetailsServ.updateBookMethod(updateBookRequestDto,id),HttpStatus.CREATED);
    }
}