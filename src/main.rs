use std::collections::VecDeque;

use clap::Parser;
use image::{ImageBuffer, Rgb, RgbImage};

/// Create fractals using sandpile© technology
#[derive(Parser, Debug)]
#[command(author, version, about, long_about = None)]
struct Args {
    /// Size of the generated fractal image (result image size will be size*size)
    #[arg(long)]
    size: usize,

    /// Initial value to populate the sandpile
    #[arg(long)]
    initial: usize,

    /// Path to write the image to
    #[arg(long)]
    path: String,
}

const MODULUS: usize = 4;

fn main() {
    let args: Args = Args::parse();
    let size = if &args.size % 2 == 0 { &args.size + 1 } else { args.size };
    let mut grid = create_grid(&size, &args.initial);
    normalize_grid(&mut grid);
    draw_fractal(&grid, &size, args.path);
}

fn create_grid(size: &usize, initial: &usize) -> Vec<Vec<usize>> {
    let mut grid = vec![vec![0; *size]; *size];
    grid[size / 2][size / 2] = *initial;
    return grid;
}

fn normalize_grid(grid: &mut Vec<Vec<usize>>) {
    let mut queue: VecDeque<(usize, usize)> = VecDeque::new();
    queue.push_front((grid.len() / 2, grid.len() / 2));
    while !queue.is_empty() {
        let c = queue.pop_back().unwrap();
        normalize_coordinate(grid, &c, &mut queue);
    }
}

fn normalize_coordinate(grid: &mut Vec<Vec<usize>>, c: &(usize, usize),
                        q: &mut VecDeque<(usize, usize)>) {
    let x = c.0;
    let y = c.1;

    let value = grid[x][y];
    if value < 4 {
        return;
    }

    grid[x][y] = value % MODULUS;

    let inc = value / MODULUS;
    let ns: Vec<(usize, usize)> = vec![(x.overflowing_sub(1).0, y), (x + 1, y), (x, y.overflowing_sub(1).0), (x, y + 1)];
    ns.iter().for_each(|n: &(usize, usize)| {
        if n.0 < grid.len() && n.1 < grid.len() {
            grid[n.0][n.1] = grid[n.0][n.1] + inc;
            let cur = grid[n.0][n.1];
            if cur >= MODULUS {
                q.push_front(*n);
            }
        }
    })
}

fn draw_fractal(grid: &Vec<Vec<usize>>, size: &usize, to: String) {
    let mut img: RgbImage = ImageBuffer::new(*size as u32, *size as u32);
    for x in 0..*size {
        for y in 0..*size {
            img.put_pixel(x as u32, y as u32, color_for(grid[x][y]))
        }
    }
    img.save(to).unwrap();
}

fn color_for(value: usize) -> Rgb<u8> {
    let color = match value {
        0 => [69, 69, 69],
        1 => [255, 96, 0],
        2 => [255, 165, 89],
        3 => [255, 230, 199],
        _ => panic!("value >= 4 encountered while drawing")
    };
    Rgb::from(color)
}