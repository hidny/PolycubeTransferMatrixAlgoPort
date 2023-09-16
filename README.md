# Transfer Matrix Algorithm Port

## Goal of this repo

Implement the algo described in https://arxiv.org/pdf/cond-mat/0007239.pdf :
"Enumerations of lattice animals and trees
Iwan Jensen
Department of Mathematics and Statistics,
The University of Melbourne,
Victoria 3010, Australia
September 11, 2018"

The reason I wanted to do it is because I thought the algorithm described in the paper was fast and interesting.

I also plan to make variants of it that find the number of 2-way rotational symmetries, and the number of 2-way reflections.

Based on the thought experiments I've had, I believe that Iwan Jensen's algorithm can be modified to find all the 180 degree rotations of the 2D lattice,
and all the 2-way reflections of the 2D lattice. I also believe that finding the number of fixed solutions for N squares is more compute heavy than finding the symmetries
because with the symmetrical searches, you have the chance to add 2 squares at once.
That's why I'm hopeful that my 10 year old laptop and my barely adequate port might reach N=56 for the 2-way symmetric cases. (N=56 is the current bleeding edge for fixed 2D lattices) 

For the 4-way symmetries and 8-way symmetries, I'm not sure if it's doable with a variant of Iwan Jensen's algorithm, but I believe it's doable by just being clever with redelmeier’s algorithm and using a 2D rotated lattice in the right way.
(I already attempted the rotational versions of it with mixed success in the PolycubeShapeRotSymCount repo (https://github.com/hidny/PolycubeShapeRotSymCount) )

### Note to self
The algo I used in that repo for rotational lattices can easily be improved by making the "ring" first, and then using redelmeier’s algorithm.
Also, the squares inside the ring can be done separately from the squares outside the hole.

### Links to more online resources
Wikipedia:
https://en.wikipedia.org/wiki/Polyomino

Online encyclopedia of integer sequences:
https://oeis.org/A001168

Kevin Gong's website:
http://kevingong.com/Polyominoes/Enumeration.html


## Notable diffs between the paper's algo and my algo

1) My signature implementation is more compact because I want to save space and not use anything more complex than a 64-bit number.

2) I had to be creative with how to figure out if the minimum number of squares required to connect it is too high. The paper didn't go into detail about it, so I came up with something on my own.
It involves 3 functions where 2 of them call each other recursively. There might be a better way, but I couldn't figure it out.
I might go into more details about that later... (For the implementation, see: RecursiveMinSquaresNeedToAttach.java)

3) I took guesses about what to do when the paper gave strange directions. For example,
the paper had: "...with an addition weight factor u on the source if the new site is occupied..."
I decided that it only makes sense if u is always 1, so in my program, u=1.
The paper's directions about how to enumerate were also a bit unclear, so I made up my own rules. Luckily, it worked out.


## Current progress

As of this writing, my program was able to correctly calculate from N=1 to 39.
At N=40, the space usage hit the max.
Getting the answer for N=39 is a far cry from Iwan Jensen's N=56 result...
I'm guessing his algo is faster, and he had more hardware...
 
## Current plan
I think I'll take a small break from this project because I'm prioritizing another coding project.
I'll definitely resume working on this project next year. I want to see if I could actually count the number of 2D reflections and rotations as efficiently as I imagine I can.


# Evolution of the Program:
For this project, I decided to keep several working versions of the algorithm in repo at the same time.

## Here's a quick summary of the different versions
Every version has its own "MainVersionX.java" file and "PatialGenX.java" file. At version 4 and above, they all use the 'RecursiveMinSquaresNeedToAttach.java' class.
At version 5 and above, they all have their own version of the 'ChineseRemainderTheoremUtilFunctions.java' file.

## Here's a slightly more detailed summary

### MainVersion2.java
I just got it to work. I ignored the logic for figuring out if the minimum number squares is too high and had a 'partialGeneratingFunction' class that took a lot of space.

### MainVersion3.java
Added a rudimentary function that figures out if the min number of squares is too high

### MainVersion4.java
Made it much faster by having a pretty good function that figures out if the min number of squares is too high.
It could technically be improved if you use the info about where the kink will be, but I skipped on making that change.
I also took away a lot of wasted space in the partial generating function class.

### MainVersion5.java
Because there was an integer overflow error because the totals went beyond a 64-bit number, I had the idea of copying the paper's strategy of holding the number of solutions as residues of primes just under 2^15. It was slower, but it worked.

### MainVersion6.java
I repeated the ideas of MainVersion5.java, but had the utility function use the long (64-bit) type instead of a BigInteger. This means long overflow is a problem, but I was hoping it would be faster. It was still a bit slow.

### MainVersion7.java
Simplified V5 and V6 by having every single stored number have 8 residues of primes barely lower than 2^15 no matter what.
This saved a lot of time because I could avoid functions that check how many residues we need. (It's always 8!)
This is almost as fast as version 4, but it doesn't overflow and there's some space savings because V4 used a class to hold the partial generating function results while this version just uses a short array (array of 16 bit numbers).

### Future version(s)
At this point, space usage is the bottle-neck. In the future, I could look into doing special logic for when the number of solutions is less than 2^15, but I'm not too motivated to do that right now.
(One example of the special logic: having the low enumeration counts share arrays to save space...)
Another thing I'm planning on doing is making variants of the algo that enumerates different kinds of symmetric solutions. This project is still a work-in-progress.