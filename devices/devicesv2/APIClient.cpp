#include "APIClient.h"


APIClient::APIClient(char* url) {
    strcpy(this->url, url);
}

void APIClient::postmessage(char* message) {
     // setup/create
  HTTPClient http;

  // init
  http.begin(this->url);
  http.addHeader("Content-Type", "application/json");

  int response = http.POST(message);

  // close
  http.end();
}
