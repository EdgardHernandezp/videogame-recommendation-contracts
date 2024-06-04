package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {
    request {
        urlPath "/fetch-recommendations"
        method HttpMethods.PUT
        headers {
            header(contentType(), applicationJson())
        }
        body([
                userId         : value(anyUuid(), producer('e9fbebcf-abb4-4097-831a-f3f2797149d8')),
                userName       : value(consumer(optional(anyAlphaUnicode()))),
                preferredGenres: ['Platformer', 'Stealth', 'Adventure']
        ])
    }
    response {
        status 200
        headers {
            header(contentType(), applicationJson())
        }
        body(
                [
                        'videogames' : [
                                [
                                        name : 'Red Dead Redemption',
                                        isGOTY: value(producer(optional('true'))),
                                        genre: 'Adventure'
                                ],
                                [
                                        name : 'Crash Bandicoot 3',
                                        genre: 'Platformer'
                                ]]
                ]
        )
        bodyMatchers {
            jsonPath('$.videogames[*].genre', byCommand('isValidGenre($it)'))
            jsonPath('$.videogames', byType {
                occurrence(2)
            })
        }
    }
    priority(2)
}