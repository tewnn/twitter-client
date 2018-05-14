package com.tewnn.twitterclient.services

import com.tewnn.twitterclient.services.statuses.IStatusesService

interface IServices {
    val statusesService: IStatusesService
}