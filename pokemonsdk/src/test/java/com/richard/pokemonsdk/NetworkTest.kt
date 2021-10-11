package com.richard.pokemonsdk

import com.google.gson.Gson
import com.richard.pokemonsdk.model.ApiError
import com.richard.pokemonsdk.networking.NetworkRequest
import io.reactivex.rxjava3.core.Observable
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NetworkTest {
    lateinit var mockWebServer: MockWebServer
    val testUrl = "http://baseTest/tets"
    val tetsMessage = "{'code': '200', 'message' : " +
            "'Sucessme', 'info' : 'Api is working'}"

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer.url(testUrl)

    }


    @Test
    fun postRequest_should_return_reflection_type_passed() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(tetsMessage))
        val response =
            NetworkRequest<ApiError>(ApiError(code = 0)).post(mockWebServer.url("/").toString(), "")
                .test();
        val expected = Gson().fromJson(tetsMessage, ApiError::class.java)
        response.assertValueAt(0, expected)
    }


    @Test
    fun postRequest_should_handle_invalid_address() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(tetsMessage))
        val response =
            NetworkRequest<ApiError>(ApiError(code = 0)).post(testUrl + "idndhdhhd", "").test();
        assertThrows<IOException> {
            return response.onNext(ApiError(code = 0))
        }
    }

    @Test
    fun postRequest_shoukd_throw_exception_on_api_error_if_not_successfull() {
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody(tetsMessage))
        val response =
            NetworkRequest<ApiError>(ApiError(code = 0)).post(mockWebServer.url("/").toString(), "")
                .test();
        assertThrows<IOException> {
            return response.onNext(ApiError(code = 0))
        }
    }

    @Test
    fun postReturnsRightValueInObservable() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("Fake response"))
        val response = NetworkRequest<String>(String()).post(mockWebServer.url("/").toString(), "");
        assert(response is Observable<*>)
    }


    @Test
    fun getRequest_should_Type_passed() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(tetsMessage))
        val response =
            NetworkRequest<ApiError>(ApiError(code = 0)).get(mockWebServer.url("/").toString())
                .test();
        val expected = Gson().fromJson(tetsMessage, ApiError::class.java)
        response.assertValueAt(0, expected)
    }

    @Test
    fun getRequest_should_handle_invalid_address() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(tetsMessage))
        val response =
            NetworkRequest<ApiError>(ApiError(code = 0)).post(testUrl + "idndhdhhd", "").test();
        assertThrows<IOException> {
            return response.onNext(ApiError(code = 0))
        }
    }

    @Test
    fun getRequest_should_return_throw_an_error_if_not_successfull() {
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody(tetsMessage))
        val response =
            NetworkRequest<ApiError>(ApiError(code = 0)).get(mockWebServer.url("/").toString())
                .test();
        assertThrows<IOException> {
            return response.onNext(ApiError(code = 0))
        }
    }
}