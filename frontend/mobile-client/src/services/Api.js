import Axios from "axios";

var dateRegExMs = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})\.{1}\d{1,7}-\d{2}:\d{2}$/;
var dateRegExZ = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})\.{1}\d{1,7}Z$/;
var dateRegEx = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})$/;
var dateRegExShort = /^(\d{4})-(\d{2})-(\d{2})$/;

function tryMatchDate(dateString) {
    if (dateString.length === 19) {
        return dateString.match(dateRegEx);
    }
    if (dateString.length === 10) {
        return dateString.match(dateRegExShort);
    }
    if (dateString.length > 22 && dateString.length < 28) {
        return dateString.match(dateRegExZ);
    }
    if (dateString.length > 26 && dateString.length < 34) {
        return dateString.match(dateRegExMs);
    }
    return false;
};

function convertDateStringsToDates(input) {
    if (typeof input !== "object")
        return input;
    for (var key in input) {
        if (!input.hasOwnProperty(key))
            continue;

        var value = input[key];
        var match;

        if (typeof value === "string" && (match = tryMatchDate(value))) {
            var date = new Date(match[1], match[2] - 1, match[3], match[4] || 0, match[5] || 0, match[6] || 0);
            input[key] = date;
        } else if (typeof value === "object") {
            convertDateStringsToDates(value);
        }
    }
    return null;
}

const Api = Axios.create({
    baseURL: process.env.REACT_APP_BACKEND,
    transformResponse: Axios.defaults.transformResponse.concat((data) => {
        convertDateStringsToDates(data);
        return data;
    })
});

const requestFulfilledInterceptor = function(request) {
    const authToken = localStorage.getItem("Authorization");
    if (authToken) {
        request.headers.authorization = authToken;
    }
    return request;
}
const requestErrorInterceptor = function(error) {
    return Promise.reject(error);
}
const responseFulfilledInterceptor = function(request) {
    return request;
}
const responseErrorInterceptor = function(error) {
    // TODO Feedback
    console.log(error.message, error.response);
    if (error.response.status === 401) {
        localStorage.removeItem("Authorization");
        window.location.replace('/');
    }
    return Promise.reject(error);
};
Api.interceptors.request.use(requestFulfilledInterceptor, requestErrorInterceptor);
Api.interceptors.response.use(responseFulfilledInterceptor, responseErrorInterceptor)

export default Api;