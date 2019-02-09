curl -X POST -H "Content-Type: application/json" -d '{"user":{"alias":"moises"},"multiplication":{"factorA":"42","factorB":"10"},"resultAttempt":"420"}' http://localhost:8080/results

curl -X POST -H "Content-Type: application/json" -d '{"user":{"alias":"moises"},"multiplication":{"factorA":"42","factorB":"10"},"resultAttempt":"420"}' http://localhost:8000/api/results/


curl -X GET http://localhost:8000/api/leaders

