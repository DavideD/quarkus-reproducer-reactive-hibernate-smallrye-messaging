import http from 'k6/http';
import {check, group, sleep} from 'k6';

const BASE_URL = 'http://host.docker.internal:8080/hello/call';

export const options = {
    stages: [
        {duration: '1m', target: 20}, // ramp up to 20 users/s within 1m
        {duration: '1m', target: 20}, // stay at 20 users for 1 minutes
        {duration: '1m', target: 0}, // ramp-down to 0 users
    ],
    thresholds: {
        'http_req_duration': ['p(99)<1500'], // 99% of requests must complete below 1.5s
        'test': ['p(99)<1500'], // 99% of requests must complete below 1.5s
    },
};


export default () => {
    const res = http.get(`${BASE_URL}?id=1`, {});

    check(res, { 'status was 202': (r) => r.status === 202 });

    sleep(1);
};
