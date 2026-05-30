import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuestManager {

    private HashMap<String, Guest> guests;

    public GuestManager() {
        guests = new HashMap<String, Guest>();
    }

    public void initGuests() {
        Guest neighbor = Guest.createNeighbor();
        addGuest(neighbor);

        Guest daughter = Guest.createDaughter();
        addGuest(daughter);

        Guest firefighter = Guest.createFirefighter();
        addGuest(firefighter);
        firefighter.setFeatures(new GuestFeatures(
                true,
                true,
                false,
                true,
                "firefighter_teeth",
                "firefighter_hands",
                "firefighter_eyes",
                "firefighter_ears"
        ));
        Guest teacher = Guest.createTeacher();
        addGuest(teacher);
        teacher.setFeatures(new GuestFeatures(
                true,
                true,
                false,
                true,
                "teacher_teeth",
                "teacher_hands",
                "teacher_eyes",
                "teacher_ears"
        ));

        Guest coat_person = Guest.createCoatperson();
        addGuest(coat_person);
        coat_person.setFeatures(new GuestFeatures(
                false,
                false,
                true,
                false,
                "coat_person_teeth",
                "coat_person_hands",
                "coat_person_eyes",
                "coat_person_ears"
        ));

        Guest judge_Visitor = Guest.createJudge();
        addGuest(judge_Visitor);

        Guest widow = Guest.createWidow();
        addGuest(widow);
        widow.setFeatures(new GuestFeatures(
                false,
                false,
                true,
                false,
                "widow_teeth",
                "widow_hands",
                "widow_eyes",
                "widow_ears"
        ));

        Guest auntie = Guest.createAuntie();
        addGuest(auntie);
        auntie.setFeatures(new GuestFeatures(
                false,
                false,
                true,
                false,
                "auntie_teeth",
                "auntie_hands",
                "auntie_eyes",
                "auntie_ears"
        ));

        Guest panic_girl = Guest.createPanicGirl();
        addGuest(panic_girl);
        panic_girl.setFeatures(new GuestFeatures(
                false,
                false,
                false,
                false,
                "panic_girl_teeth",
                "panic_girl_hands",
                "panic_girl_eyes",
                "panic_girl_ears"
        ));
        Guest collector = Guest.createCollector();
        addGuest(collector);
    }

    public void addGuest(Guest guest) {
        if (guest == null) {
            return;
        }

        guests.put(guest.getId(), guest);
    }

    public Guest getGuest(String id) {
        return guests.get(id);
    }

    public void moveGuestToRoom(String guestId, DayRoomType room) {
        Guest guest = getGuest(guestId);

        if (guest == null) {
            return;
        }

        guest.setInsideHouse(true);
        guest.setCurrentRoom(room);
    }

    public void removeGuestFromHouse(String guestId) {
        Guest guest = getGuest(guestId);

        if (guest == null) {
            return;
        }

        guest.setInsideHouse(false);
        guest.setCurrentRoom(null);
    }

    public List<Guest> getGuestsInRoom(DayRoomType room) {
        List<Guest> result = new ArrayList<Guest>();

        for (Guest guest : guests.values()) {
            if (guest.isInsideHouse() && guest.getCurrentRoom() == room) {
                result.add(guest);
            }
        }

        return result;
    }

    public List<Guest> getAllGuests() {
        return new ArrayList<Guest>(guests.values());
    }

}
