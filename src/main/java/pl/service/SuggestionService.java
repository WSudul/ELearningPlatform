package pl.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.model.*;
import pl.repository.CourseRepository;
import pl.repository.TagRepository;

import java.util.*;

@Service
public class SuggestionService {

    private final long score50Percent = 5;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CourseRepository courseRepository;


    public List<Course> findCoursesSharingTags(Collection<Tag> tags) {

        Set<String> tagNames = new HashSet<>();
        tags.forEach(tag -> tagNames.add(tag.getName()));

        List<Course> commonCourses = courseRepository.findByTagSetName(tagNames);

        return commonCourses;
    }

    public Set<Course> suggestCourseToUser(User user) {

        Set<Usercourses> usercourses = user.getUsercourses();

        if (usercourses.isEmpty()) {
            return getTopRatedCourses(10);
        }

        Map<Tag, Integer> tagCountMap = new HashMap<>();

        for (Usercourses userCourse : usercourses) {
            Set<Review> reviews = userCourse.getReview();

            if (!reviews.isEmpty()) {
                Review latestReview = Collections.max(reviews,
                        Comparator.comparing(o -> o.getDateAdded().toInstant()));

                if (latestReview.getScore() > score50Percent) {
                    for (Tag tag : userCourse.getCourse().getTagSet()) {
                        if (tagCountMap.containsKey(tag))
                            tagCountMap.put(tag, tagCountMap.get(tag) + 1);
                        else
                            tagCountMap.put(tag, 1);
                    }

                }
            }
        }

        List<Tag> topTags = new ArrayList<>();
        tagCountMap.entrySet().stream()
                .sorted(Map.Entry.<Tag, Integer>comparingByValue().reversed()).limit(10)
                .forEach(mapEntry -> topTags.add(mapEntry.getKey()));


        //#TODO rework this naive version

        Set<Course> courses = new HashSet<>();

        topTags.forEach(tag -> courses.addAll(
                this.findCoursesSharingTags(
                        new HashSet<>(Arrays.asList(tag))
                )));

        return courses;
    }


    //#TODO implement this
    public Set<Course> getTopRatedCourses(int count) {

        if (count < 0)
            return null;


        return new HashSet<Course>();


    }


}
