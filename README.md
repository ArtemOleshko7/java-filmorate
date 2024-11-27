java-filmorate
A service that works with movie ratings from users and top recommendations for viewing.

All CRUD-operations with films / users

UPDATE films
SET FILM_NAME = 'Avatar',
DESCRIPTION = 'avatar description',
RELEASE_DATE = '2009-10-17',
DURATION = 162,
RATING = 10,
MPA_RATING_ID = 3
WHERE FILM_ID = 1;

Users can add / delete like to films
Users can add / delete friends
Users can request shared friends
Users can request top-rated films

SELECT FILMS.*, COUNT(l.FILM_ID) AS film_count
FROM FILMS
LEFT JOIN LIKES AS l ON FILMS.FILM_ID = l.FILM_ID
GROUP BY FILMS.FILM_ID
ORDER BY film_count DESC
LIMIT 10;