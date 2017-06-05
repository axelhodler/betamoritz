package co.hodler.boundaries.http;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ParsedRequestTest {
  @Test
  public void deal_with_empty_strings() {
    ParsedRequest parsedRequest = new ParsedRequest("");

    assertThat(parsedRequest.getPath(), is(""));
  }

  @Test
  public void deal_with_null() {
    ParsedRequest parsedRequest = new ParsedRequest(null);

    assertThat(parsedRequest.getPath(), is(""));
  }

  @Test
  public void deals_with_nonsense() {
    ParsedRequest parsedRequest = new ParsedRequest("yooooo");

    assertThat(parsedRequest.getPath(), is(""));
  }

  @Test
  public void extract_the_path() {
    ParsedRequest parsedRequest = new ParsedRequest("GET / HTTP/1.1");

    assertThat(parsedRequest.getPath(), is("/"));
  }
}
