#This will run a load-test hitting the /hello/call endpoint
docker run --rm --name k6 -i loadimpact/k6 run - <test-call.js