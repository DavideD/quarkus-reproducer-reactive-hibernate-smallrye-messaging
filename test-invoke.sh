#This will run a load-test hitting the /hello/invoke endpoint
docker run --rm --name k6 -i loadimpact/k6 run - <test-invoke.js