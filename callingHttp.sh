#!/bin/bash
s=$(date)

echo -e "\n$s Start"
for i in {1..10}
do
  # to use a single echo statement with the -e flag and embedded newline characters \n:
  echo -e "\ncalling " $i
  curl http://localhost:8080/Servlet-Project/urlrewriting.html
  curl http://localhost:8080/Servlet-Project/login.html
  curl http://localhost:8080/Servlet-Project/form
  curl http://localhost:8080/Servlet-Project/simple
  curl http://localhost:8080/Servlet-Project/listener/countries.jsp
done

t=$(date)
echo -e "\n$t End"
