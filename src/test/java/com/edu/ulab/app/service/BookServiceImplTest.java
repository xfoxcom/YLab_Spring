package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookJpaRepository;
import com.edu.ulab.app.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Тестирование функционала {@link com.edu.ulab.app.service.impl.BookServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing book functionality.")
public class BookServiceImplTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookJpaRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @Test
    @DisplayName("Создание книги. Должно пройти успешно.")
    void saveBook_Test() {
        //given
        Person person  = new Person();
        person.setId(1L);

        BookDto bookDto = new BookDto();
        bookDto.setUser_id(1L);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUser_id(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book book = new Book();
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setUser_id(1L);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setUser_id(1L);

        //when

        when(bookMapper.bookDtoToBook(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);

        //then
        BookDto bookDtoResult = bookService.createBook(bookDto);
        assertEquals(1L, bookDtoResult.getId());
    }


    @Test
    @DisplayName("Обновление книги. Должно пройти успешно.")
    void updateBook() {
        //Given
        BookDto bookDto = new BookDto();
        bookDto.setId(100L);
        bookDto.setUser_id(200L);
        bookDto.setAuthor("new author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        Book book = new Book();
        book.setId(100L);
        book.setUser_id(200L);
        book.setAuthor("new author");
        book.setTitle("test title");
        book.setPageCount(1000);

        Book bookSaved = new Book();
        bookSaved.setId(100L);
        bookSaved.setUser_id(200L);
        bookSaved.setAuthor("new author");
        bookSaved.setTitle("test title");
        bookSaved.setPageCount(1000);

        BookDto bookChanged = new BookDto();
        bookChanged.setId(100L);
        bookChanged.setUser_id(200L);
        bookChanged.setAuthor("new author");
        bookChanged.setTitle("test title");
        bookChanged.setPageCount(1000);

        //When
        when(bookRepository.existsById(bookDto.getId())).thenReturn(true);
        when(bookMapper.bookDtoToBook(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(bookSaved);
        when(bookMapper.bookToBookDto(bookSaved)).thenReturn(bookChanged);

        //Then
        BookDto bookDtoResult = bookService.updateBook(bookDto);
        assertEquals("new author", bookDtoResult.getAuthor());

    }

    @Test
    @DisplayName("Получение книги по id. Должно пройти успешно.")
    void getBook_Test() {
        //Given
        Book book = new Book();
        book.setId(101L);
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setUser_id(200L);

        BookDto bookDto = new BookDto();
        bookDto.setId(101L);
        bookDto.setPageCount(1000);
        bookDto.setTitle("test title");
        bookDto.setAuthor("test author");
        bookDto.setUser_id(200L);

        //When
        when(bookRepository.existsById(book.getId())).thenReturn(true);
        when(bookRepository.getReferenceById(book.getId())).thenReturn(book);
        when(bookMapper.bookToBookDto(book)).thenReturn(bookDto);

        //Then
        BookDto bookDtoResult = bookService.getBookById(101L);
        assertEquals(101, bookDtoResult.getId());

    }

    @Test
    @DisplayName("Удаление книги по id. Должно пройти успешно.")
    void deleteBook_Test() {
        //Given
        Book book = new Book();
        book.setId(100L);
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setUser_id(200L);

        //When
        when(bookRepository.existsById(100L)).thenReturn(true);

        //Then
        bookService.deleteBookById(100L);

        verify(bookRepository, times(1)).deleteById(book.getId());

    }

    @Test
    @DisplayName("Fail test. Выбрасывает NotFoundException, когда нет книги с id.")
    void getUserTest_NotFountException() {

        //When
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        //Then
        assertThrows(NotFoundException.class, () -> bookService.getBookById(100L));

    }

    @Test
    @DisplayName("Fail test на получение книги по id. Выводится сообщение, когда нет книги с id.")
    void willThrowWhen () {
        //Given
        Book book = new Book();
        book.setId(100L);
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setUser_id(200L);

        assertThatThrownBy(() -> bookService.getBookById(book.getId())).hasMessageContaining("No Book with id:  " + book.getId());

    }

}
