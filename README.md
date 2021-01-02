Demo:

Create a new shortened URL:

```
curl -X POST localhost:8080/new?originalURL=https://github.com
```

Response:

```json
{
    "shortUrl": "JQGkw3",
    "originalUrl": "https://github.com",
    "created_at": "2020-12-30T22:19:23.238474",
    "_links": {
        "self": {
            "href": "http://localhost:8080/urls/JQGkw3"
        },
        "urls": {
            "href": "http://localhost:8080/urls"
        }
    }
}
```


Get the original URL given a shortened URL:
```
curl localhost:8080/urls/JQGkw3
```
Response:
```json
{
    "shortUrl": "JQGkw3",
    "originalUrl": "https://github.com",
    "created_at": "2020-12-30T22:19:23.238474",
    "_links": {
        "self": {
            "href": "http://localhost:8080/urls/JQGkw3"
        },
        "urls": {
            "href": "http://localhost:8080/urls"
        }
    }
}
```

The following endpoints can be used to see how many times a given short URL has been used.

| Endpoint        | Response           
| ------------- |:-------------:| 
| `/analytics/{shortUrl}/days `     | number of uses per day | 
| `/analytics/{shortUrl}/months`     | number of uses per month     |   
| `/analytics/{shortUrl}/years` | number of uses per year    | 
|   `/analytics/{shortUrl}/dates?start=date&end=date2` | return uses of the url between start and end date |
 |   `/analytics/{shortUrl}/counts?start=date&end=date2` | return the number of uses between start and end date |



