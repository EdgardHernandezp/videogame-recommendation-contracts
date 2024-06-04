package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {
    request {
        urlPath('/fetch-recommendations') {
            queryParameters {
                parameter('exclusions', equalTo('Platformer'))
            }
        }
        method(HttpMethods.PUT)
        headers {
            header(contentType(), applicationJson())
        }
        body([
                'userId'         : anyUuid(),
                'preferredGenres': ['Platformer', 'Stealth', 'Adventure']
        ]
        )
    }
    response {
        headers {
            header(contentType(), applicationJson())
        }
        status(200)
        body(
                [
                        'videogames': [
                                [
                                        'name' : 'Red Dead Redemption',
                                        'genre': 'Adventure'
                                ]
                        ],
                        'exclusions' : fromRequest().query('exclusions')
                ]
        )
    }
    priority(1)
}