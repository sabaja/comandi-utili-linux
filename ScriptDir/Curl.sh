curl -X POST -H "Content-Type: application/json" -d '{"user":{"alias":"moises"},"multiplication":{"factorA":"42","factorB":"10"},"resultAttempt":"420"}' http://localhost:8080/results

curl -X POST -H "Content-Type: application/json" -d '{"user":{"alias":"moises"},"multiplication":{"factorA":"42","factorB":"10"},"resultAttempt":"420"}' http://localhost:8000/api/results/

curl -k POST "https://api.mastercard.com/mastercom/v4/claims/200002020654/chargebacks/200002052146/reversal" -H "accept: application/json" -d '{"claim-id":"200002020654","currency":"USD","documentIndicator":"false","messageText":"This is a test message text","amount":"100.00","reasonCode":"4853","isPartialChargeback":"false","chargebackType":"SECOND_PRESENTMENT"}'


curl -X GET http://localhost:8000/api/leaders

