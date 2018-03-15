# skip test
```
  $ gradle --refresh-dependencies build -x test
```
  
# Hello Http tutorial
## [同步Hello Http](http://localhost:9000/hello)  
## [异步Hello Http](http://localhost:9000/helloasyncgrpc) 
 
# graphql tutorial
## [Getting Started with GraphQL and Spring Boot](http://www.baeldung.com/spring-graphql)
## [Input object type as an argument for GraphQL mutations and queries](https://medium.com/graphql-mastery/json-as-an-argument-for-graphql-mutations-and-queries-3cd06d252a04)
## [http://localhost:9000/graphiql](http://localhost:9000/graphiql)
### 查询列表
#### step1：调用API
```graphql
query recentPosts {
  recentPosts(count: 100, offset: 0) {
    id
    title
    text
    category
    author {
      id
      name
      thumbnail
    }
  }
}
```
#### step2：查看结果
```json
{
  "data": {
    "recentPosts": [
      {
        "id": "758f5824-297a-4a45-b0fd-3e9af51cd931",
        "title": "title-write1",
        "category": "category-write1",
        "author": {
          "id": "Author0",
          "name": "Author 0",
          "thumbnail": "http://example.com/authors/0"
        }
      },
      {
        "id": "72c06006-8ba2-4158-9c1e-efb03e3b4a9c",
        "title": "title-input2",
        "category": "category-input2",
        "author": {
          "id": "Author2",
          "name": "Author 2",
          "thumbnail": "http://example.com/authors/2"
        }
      }
    ]
  }
}
```
### 作者Author0写了一个帖子
#### step1：调用API
```graphql
mutation writePost{
  writePost(title: "title-write1", text: "text-write1", category: "category-write1", author: "Author0") {
    title
    text
    category
    author {
      id
      name
    }
  }
}

```
#### step2：查看结果
```json
{
  "data": {
    "writePost": {
      "title": "title-write1",
      "text": "text-write1",
      "category": "category-write1",
      "author": {
        "id": "Author0",
        "name": "Author 0"
      }
    }
  }
}
```

###作者Author2用简化方式写了一个帖子
#### step1：调用API
```graphql
mutation writePostWithInput($input:PostInput!){
  writePostWithInput(input:$input) {
    title
    text
    category
    author {
      id
      name
    }
  }
}

```
#### step2：填充查询变量(Query Variables)
```json
{
  "input": {
    "title": "title-input2",
    "text": "text-input2",
    "category": "category-input2",
    "authorId":  "Author2"
  }
}
```  
#### step3：查看结果
```json
{
  "data": {
    "writePostWithInput": {
      "title": "title-input2",
      "text": "text-input2",
      "category": "category-input2",
      "author": {
        "id": "Author2",
        "name": "Author 2"
      }
    }
  }
}
```
