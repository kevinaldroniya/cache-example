1. # **Project Documentation**
2. 
3. ## **1. Overview**
4. This service is designed to store and manage the availability status of services. It notifies users about the current status, allowing them to make informed decisions and explore alternative methods for performing actions or processes when necessary.
5. 
6. ---
7. 
8. ## **2. Key Features**
9. 1. **Save Service Data**: Allows saving hierarchical service data.
10. 2. **Retrieve All Data**: Retrieves all service data.
11. 3. **Query by Path**: Supports querying specific nested data using dot-separated paths (e.g., `serviceName.subKey`).
12. 
13. ---
14. 
15. ## **3. Technologies Used**
16. - **Programming Language**: Java 23 (Amazon Corretto)
17. - **Framework**: Spring Boot 3.4.1
18. - **Database**: Redis
19. - **Build Tool**: Gradle
20. - **Testing**: JUnit 5
21. 
22. ---
23. 
24. ## **4. Project Structure**
25. 
26. ### **4.1. Controller Layer**
27. - **Class**: `GeneralController`
28. - **Endpoints**:
29.     1. **POST /api/services**
30.         - Saves the provided service data.
31.         - **Input**: JSON map with service names as keys and data as values.
32.         - **Response**: `"Data saved"`
33.     2. **GET /api/services**
34.         - Retrieves all service data.
35.         - **Response**: JSON map of all service data.
36.     3. **GET /api/services/{keyPath}**
37.         - Retrieves specific data by a dot-separated key path.
38.         - **Input**: Path variable `keyPath`.
39.         - **Response**: Nested data matching the key path or a 404 error if not found.
40. 
41. ---
42. 
43. ### **4.2. Service Layer**
44. - **Class**: `GeneralService`
45. - **Responsibilities**:
46.     1. **saveData(Map<String, Object> general)**:
47.         - Saves hierarchical data with  keys prefixed by `service_data:`.
48.     2. **getAllData()**:
49.         - Retrieves all service data stored under keys prefixed with `service_data:`.
50.     3. **getValueByPath(String keyPath)**:
51.         - Traverses the nested structure using a dot-separated path to retrieve specific data.
52.     - **Helper Method**: `getKeyForService(String serviceName)`
53.         - Constructs keys for service data.
54. 
55. ---
56. 
57. 
58. ## **7. Sample Usage**
59. 1. **Save Data**:
60.     - Endpoint: `POST /api/services`
61.     - Request Body:
62.       ```json
63.       {
64.         "serviceA": {
65.           "key1": "value1",
66.           "key2": "value2"
67.         },
68.         "serviceB": {
69.           "key1": "value3",
70.           "key2": {
71.             "nestedKey": "nestedValue"
72.           }
73.         }
74.       }
75.       ```
76.     - Response: `"Data saved"`
77. 
78. 2. **Retrieve All Data**:
79.     - Endpoint: `GET /api/services`
80.     - Response:
81.       ```json
82.       {
83.         "serviceA": {
84.           "key1": "value1",
85.           "key2": "value2"
86.         },
87.         "serviceB": {
88.           "key1": "value3",
89.           "key2": {
90.             "nestedKey": "nestedValue"
91.           }
92.         }
93.       }
94.       ```
95. 
96. 3. **Query by Path**:
97.     - Endpoint: `GET /api/services/serviceB.key2.nestedKey`
98.     - Response:
99.       ```json
100.       {
101.         "serviceB.key2.nestedKey": "nestedValue"
102.       }
103.       ```
104. 
105. ---
106. 
107. ## **8. Error Handling**
108. - **Data Not Found**: Returns a 404 status with an error message if the requested path does not exist in Redis.
109. # cache-example
