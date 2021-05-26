#ifndef API_INCLUDED
#define API_INCLUDED

#include <HTTPClient.h>

class APIClient {

    private:
        char url[60];

    public:
        APIClient(char* url);

        void postmessage(char* message);

};

#endif