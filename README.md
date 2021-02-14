## How to run
I prepared only dev env, to run app use java 15 (hotspot is better):
1. run docker compose in project `docker-compose up`
1. run `./gradlew bootRun`

## Endpoints
### GET `/books`
List all books

Url params:
* `page` - page number
* `size` - page size

Example response:
```
{
  "pageInfo": {
    "total": 1,
    "currentPage": 0,
    "totalPages": 1
  },
  "books": [
    {
      "id": "3dbfe023-2a83-409c-a785-7b52c6e1707f",
      "title": "Book title",
      "author": "Grafoman",
      "isbn": "String",
      "numberOfPages": 123,
      "rating": 3,
      "comments": [
      ]
    }
  ]
}
```

### POST `/books`
Create new book

Example request in file `request.http`

### PUT `/books/{bookId}`
Update book request

Example request in file `request.http`

### POST `/books/{bookId}/comments/`
Add comment to book

Example request in file `request.http`


### DELETE `/books/{bookId}`
Delete book with given uuid. Always responds with `200`.


## Problems found
1. Mockk works with `Hotspot` java version. I had to install it. Bug described [here](https://github.com/mockk/mockk/issues/517)
1. I don't like solution for searching last 5 comments. I tried `@Batchsize` but it isn't working. For sure there can be written better sql. Solution can be fetching all comments to map in one query. 
1. I didn't wrote real integration test
1. I haven't wrote logic for isbn
1. I haven't prepared building docker image to docker compose using `./gradlew bootBuildImage`
