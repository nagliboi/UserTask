
package ge.bog.hw22.services;

import ge.bog.hw22.exceptions.UserIdentificationNotUniqueException;
import ge.bog.hw22.exceptions.UserIdentificationNullException;
import ge.bog.hw22.modules.User;

public interface UserServiceInt {
     User userGetterService(long id);
     Long userCreatorService(User user) throws UserIdentificationNotUniqueException, UserIdentificationNullException;
     User userUpdateService(Long id, User user);
     String deleteUser(long id);
}