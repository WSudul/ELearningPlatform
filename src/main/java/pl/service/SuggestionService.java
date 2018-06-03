package pl.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.model.*;
import pl.repository.CourseRepository;
import pl.repository.ReviewRepository;
import pl.repository.TagRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SuggestionService {

    private final long score50Percent = 5;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ReviewRepository reviewRepository;



    public List<Course> findCoursesSharingTags(Collection<Tag> tags) {

        Set<String> tagNames = new HashSet<>();
        tags.forEach(tag -> tagNames.add(tag.getName()));

        List<Course> commonCourses = courseRepository.findByTagSetName(tagNames);

        return commonCourses;
    }

    public Set<Course> suggestCourseToUser(User user) {
        final int kTopTagLimit = 3; //limit to top 3 because not all courses share many same tags.

        Set<Usercourses> usercourses = user.getUsercourses();

        if (usercourses.isEmpty()) {
            return getTopNRatedCourses(10);
        }

        Map<Tag, Integer> tagCountMap = createTagCountMap(usercourses);
        List<Tag> topTags = new ArrayList<>();

        tagCountMap.entrySet().stream()
                .sorted(Map.Entry.<Tag, Integer>comparingByValue().reversed()).limit(kTopTagLimit)
                .forEach(mapEntry -> topTags.add(mapEntry.getKey()));


        Set<Course> suggestedCourses = courseRepository
                .findByTagSetName(topTags.stream().map(Tag::getName).collect(Collectors.toSet())).stream().collect(
                        Collectors.toSet());

        return suggestedCourses;
    }

    private void updateTagCount(Set<Tag> tags, Map<Tag, Integer> mapCount) {
        for (Tag tag : tags) {
            if (mapCount.containsKey(tag))
                mapCount.put(tag, mapCount.get(tag) + 1);
            else
                mapCount.put(tag, 1);
        }
    }

    private Map<Tag, Integer> createTagCountMap(Iterable<Usercourses> usercourses) {
        Map<Tag, Integer> tagCountMap = new HashMap<>();
        for (Usercourses userCourse : usercourses) {
            Set<Review> reviews = userCourse.getReview();

            if (!reviews.isEmpty()) {
                Review latestReview = Collections.max(reviews,
                        Comparator.comparing(o -> o.getDateAdded().toInstant()));

                if (latestReview.getScore() > score50Percent) {
                    updateTagCount(userCourse.getCourse().getTagSet(), tagCountMap);
                }
            }
        }

        return tagCountMap;
    }


    public Set<Course> getTopNRatedCourses(int n) {
        Map<Course, Double> courseAverageScoreMap = new HashMap<>();
        for (Course course : courseRepository.findAll()) {
            courseAverageScoreMap.put(course, (calculateAverageScoreForCourse(course)));
        }

        if (courseAverageScoreMap.size() > n) {
            List<Map.Entry<Course, Double>> entries = courseAverageScoreMap.entrySet().stream()
                    .collect(Collectors.toList());
            //sort the list by avg score descending
            Collections.sort(entries, (e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));

            //extract the top N results
            List<Map.Entry<Course, Double>> subList = entries.subList(0, n);
            return subList.stream().map(Map.Entry::getKey).collect(Collectors.toSet());


        } else
            return courseAverageScoreMap.keySet();


    }

    public Double calculateAverageScoreForCourse(Course course) {
        Double sumOfScores = 0d;
        Long reviewCounts = 0l;
        Set<Usercourses> usercourses = course.getUsercourses();
        for (Usercourses usercourse : usercourses) {
            Set<Review> reviews = usercourse.getReview();
            reviewCounts += reviews.size();
            for (Review review : reviews)
                sumOfScores += review.getScore();
        }

        return (sumOfScores / reviewCounts);

    }


}
